import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FileService } from 'src/app/service/file.service';
import { SearchQueryDTO, SearchResult, SearchService } from 'src/app/service/search.service';
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

  constructor( private router: Router,
    private fileService: FileService, private searchService: SearchService) {}

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
    if (this.keywords.trim() === '') {
      Swal.fire({
        icon: 'warning',
        title: 'Warning',
        text: 'Please enter keywords to search.',
      });
      return;
    }
    this.searchResult = null;
    const query: SearchQueryDTO = { keywords: this.keywords.split(',').map(keyword => keyword.trim()) };
    this.searchService.searchContractByContent(query).subscribe(
      result => {
        this.searchResult = result;
        Swal.fire({
          icon: 'success',
          title: 'Search Complete!',
          text: 'Results have been successfully retrieved.',
        });
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

}
