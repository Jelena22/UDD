package com.example.ddmdemo.service.impl;

import com.example.ddmdemo.dto.GeolocationResponseDTO;
import com.example.ddmdemo.dto.LocationDTO;
import com.example.ddmdemo.exceptionhandling.exception.LoadingException;
import com.example.ddmdemo.indexmodel.ContractIndex;
import com.example.ddmdemo.indexmodel.LawIndex;
import com.example.ddmdemo.indexrepository.ContractIndexRepository;
import com.example.ddmdemo.indexrepository.LawIndexRepository;
import com.example.ddmdemo.infrastructure.LocationClient;
import com.example.ddmdemo.model.Contract;
import com.example.ddmdemo.model.Law;
import com.example.ddmdemo.request.ParsedContract;
import com.example.ddmdemo.service.interfaces.ContractService;
import com.example.ddmdemo.service.interfaces.FileService;
import com.example.ddmdemo.service.interfaces.IndexingService;
import com.example.ddmdemo.service.interfaces.LawService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.language.detect.LanguageDetector;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import static com.example.ddmdemo.constant.Constants.CONTRACT_GOVERNMENT_REGEX;
//import static com.example.ddmdemo.constant.Constants.NAME_AND_SURNAME_REGEX;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService {

    private final FileService fileService;

    private final LanguageDetector languageDetector;

    private final LawIndexRepository lawIndexRepository;

    private final LawService lawService;

    private final ContractService contractService;

    private final ContractIndexRepository contractIndexRepository;

    private final LocationClient locationClient;

    @Value("${geolocation.api.key}")
    private String apiKey;

    private final String CONTRACT_GOVERNMENT_REGEX = "Uprava za (.*?), nivo uprave: (.*?), (.*?), (.*?), (.*?) u daljem tekstu klijent.";
    private final String NAME_AND_SURNAME_REGEX = "Ukupna cena:.*?\\n(\\S+\\s*\\S+\\s*\\S+).*";

    private String extractDocumentContent(MultipartFile multipartPdfFile) {
        String documentContent;
        try (var pdfFile = multipartPdfFile.getInputStream()) {
            var pdDocument = PDDocument.load(pdfFile);
            var textStripper = new PDFTextStripper();
            documentContent = textStripper.getText(pdDocument);
            pdDocument.close();
        } catch (IOException e) {
            throw new LoadingException("Error while trying to load PDF file content.");
        }

        return documentContent;
    }

    @Override
    @Transactional
    public void indexLaws(final List<MultipartFile> files) {
        files.forEach(file -> {
            LawIndex lawIndex = new LawIndex();

            String documentContent = extractDocumentContent(file);
            lawIndex.setContent(documentContent);
            lawIndex.setTitle(file.getOriginalFilename());
            String serverFilename = fileService.store(file, UUID.randomUUID().toString());
            lawIndex.setServerFilename(serverFilename);

            Law law = lawService.save(file, serverFilename);

            lawIndex.setDatabaseId(law.getId());
            lawIndexRepository.save(lawIndex);
        });
    }

    @Override
    @Transactional
    public void indexContract(final List<MultipartFile> contracts){
        contracts.forEach(contract -> {
            ParsedContract parsedContract = parseContract(contract);
            if (parsedContract.getAddress() != null && !parsedContract.getAddress().isEmpty()){
                GeolocationResponseDTO geolocationResponse = locationClient.getGeolocation(
                        apiKey, parsedContract.getAddress(), "json"
                );

                ContractIndex contractIndex = new ContractIndex();

                if (!geolocationResponse.results().isEmpty()){
                    LocationDTO coordinates = geolocationResponse.results().get(0);
                    contractIndex.setLocation(new GeoPoint(coordinates.lat(), coordinates.lon()));
                }
                contractIndex.setContent(parsedContract.getContent());
                contractIndex.setName(parsedContract.getName());
                contractIndex.setSurname(parsedContract.getSurname());
                contractIndex.setGovernmentName(parsedContract.getGovernmentName());
                contractIndex.setAdministrationLevel(parsedContract.getAdministrationLevel());
                contractIndex.setAddress(parsedContract.getAddress());

                var serverFilename = fileService.store(contract, UUID.randomUUID().toString());
                contractIndex.setServerFilename(serverFilename);

                Contract savedContract = contractService.save(contract, serverFilename);

                contractIndex.setDatabaseId(savedContract.getId());
                contractIndex.setTitle(contract.getOriginalFilename());
                System.out.println(contractIndex.getName());
                contractIndexRepository.save(contractIndex);
            }
        });
    }

    public ParsedContract parseContract(final MultipartFile contract){

        String documentContent = extractDocumentContent(contract);
        Pattern pattern = Pattern.compile(NAME_AND_SURNAME_REGEX, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(documentContent);

        String name = "";
        String surname = "";
        if (matcher.find()) {
            String[] parts = matcher.group(1).split("\\s+");
            name = parts[0];
            surname = parts[1];

            System.out.println("Potpisnik ugovora: " + name + " " + surname);
        }

        pattern = Pattern.compile(CONTRACT_GOVERNMENT_REGEX);
        matcher = pattern.matcher(documentContent);

        return matcher.find()? new ParsedContract(
                name,
                surname,
                matcher.group(1),
                matcher.group(3) + " " + matcher.group(4) + " " + matcher.group(5),
                matcher.group(2),
                documentContent
        ) : new ParsedContract();
    }
}