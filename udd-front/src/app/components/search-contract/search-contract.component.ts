import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { FileService } from 'src/app/service/file.service';
import { ContractSearchResult, SearchQueryContractByGovernmentNameAnsLevelDTO, SearchQueryContractByNameAndSurnameDTO, SearchQueryDTO, SearchResult, SearchResult1, SearchResultGovernment, SearchService } from 'src/app/service/search.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-search-contract',
  templateUrl: './search-contract.component.html',
  styleUrls: ['./search-contract.component.css']
})
export class SearchContractComponent implements OnInit {

  files: File[] = [];
  keywords: string = '';
  searchResult: SearchResult | null = null;
  searchResult1: SearchResult1 | null = null;
  searchResultGovernment: SearchResultGovernment | null = null;
  totalPages: number = 0;
  totalElements: number = 0;
  currentPage: number = 0;
  pageSize: number = 4;
  name: string = '';
  surname: string = '';
  governmentName: string = '';
  administrationLevel: string = '';

  constructor( private router: Router,
    private fileService: FileService, private searchService: SearchService) {

    }

  ngOnInit(): void {

  }

  onFileSelected(event: any){
    if (event.target.files) {
      this.files = Array.from(event.target.files);
    }
  }

  onUpload(){
    if (this.files.length > 0) {
      this.fileService.uploadContracts(this.files).subscribe(
        response => {
          Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Upload successful!',
          }) 
          console.log('Upload successful', response);
        },
        error => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Upload failed.',
          })
          console.error('Upload failed', error);
        }
      );
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'No files selected.',
      })
      console.warn('No files selected');
    }
  }

  onSearch(): void {
    if (this.keywords === '') {
      Swal.fire({
        icon: 'warning',
        title: 'Warning',
        text: 'Please enter keywords to search.',
      });
      return;
    }
    //this.searchResult = null;
    const isFirstSearch = !this.searchResult
    const query: SearchQueryDTO = { keywords: this.keywords.split(',').map(keyword => keyword.trim()) };
    this.searchService.searchContractByContent(query, this.currentPage, this.pageSize).subscribe(
      result => {
        this.searchResult = result;
        this.totalPages = result.totalPages;
        console.log(this.totalPages);
        this.totalElements = this.searchResult.totalElements;
        if (isFirstSearch) {
          Swal.fire({
            icon: 'success',
            title: 'Search Complete!',
            text: 'Results have been successfully retrieved.',
          });
        }
      },
      error => {
        Swal.fire({
          icon: 'error',
          title: 'Search Failed',
          text: 'There was an error processing your search.',
        });
        console.error('Search failed', error);
      }
    );
  }

  onSearchByNameSurname(): void {
    if (this.name === '' && this.surname === '') {
      Swal.fire({
        icon: 'warning',
        title: 'Warning',
        text: 'Please enter keywords to search.',
      });
      return;
    }
    const query: SearchQueryContractByNameAndSurnameDTO = {
      name: this.name,
      surname: this.surname
    };
    const isFirstSearch = !this.searchResult1;
    this.searchService.searchContractByNameSurname(query, this.currentPage, this.pageSize).subscribe(
      result => {
        this.searchResult1 = result;
        console.log(result);
        this.totalPages = result.totalPages;
        console.log(this.totalPages);
        this.totalElements = this.searchResult1.totalElements;
        if (isFirstSearch) {
          Swal.fire({
            icon: 'success',
            title: 'Search Complete!',
            text: 'Results have been successfully retrieved.',
          });
        }
      },
      error => {
        Swal.fire({
          icon: 'error',
          title: 'Search Failed',
          text: 'There was an error processing your search.',
        });
        console.error('Search failed', error);
      }
    );
  }

  onSearchByNameLevel(): void {
    if (this.governmentName === '' && this.administrationLevel === '') {
      Swal.fire({
        icon: 'warning',
        title: 'Warning',
        text: 'Please enter keywords to search.',
      });
      return;
    }
    const query: SearchQueryContractByGovernmentNameAnsLevelDTO = {
      governmentName: this.governmentName,
      administrationLevel: this.administrationLevel
    };
    const isFirstSearch = !this.searchResultGovernment;
    this.searchService.searchContractByNameLevel(query, this.currentPage, this.pageSize).subscribe(
      result => {
        this.searchResultGovernment = result;
        console.log(result);
        this.totalPages = result.totalPages;
        console.log(this.totalPages);
        this.totalElements = this.searchResultGovernment.totalElements;
        if (isFirstSearch) {
          Swal.fire({
            icon: 'success',
            title: 'Search Complete!',
            text: 'Results have been successfully retrieved.',
          });
        }
      },
      error => {
        Swal.fire({
          icon: 'error',
          title: 'Search Failed',
          text: 'There was an error processing your search.',
        });
        console.error('Search failed', error);
      }
    );
  }

  onPageChange(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      console.log(this.totalPages);
      this.currentPage = page;
      this.onSearchByNameLevel();
    }
  }

  onPageChange1(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      console.log(this.totalPages);
      this.currentPage = page;
      this.onSearchByNameSurname();
    }
  }

  onPageChange2(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      console.log(this.totalPages);
      this.currentPage = page;
      this.onSearch();
    }
  }

}
