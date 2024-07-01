import { Component, OnInit } from '@angular/core';
import { SearchQueryDTO, SearchResult, SearchResult1, SearchService } from 'src/app/service/search.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-search-contract-advanced',
  templateUrl: './search-contract-advanced.component.html',
  styleUrls: ['./search-contract-advanced.component.css']
})
export class SearchContractAdvancedComponent implements OnInit{

  keywords: string = '';
  searchResult: SearchResult1 | null = null;
  totalPages: number = 0;
  totalElements: number = 0;
  currentPage: number = 0;
  pageSize: number = 4;
  keywords1: string = '';
  searchResult1: SearchResult | null = null;
  keywordsLaw: string = '';

  constructor(private searchService: SearchService) {}

  ngOnInit(): void {
   
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
    const isFirstSearch = !this.searchResult;

    const query: SearchQueryDTO = {
        keywords:  this.keywords.split(' ').filter(keyword => keyword.trim() !== '')
    };

    console.log(query);
    this.searchService.searchContractAdvanced(query, this.currentPage, this.pageSize).subscribe(
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

  onPageChange(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      console.log(this.totalPages);
      this.currentPage = page;
      this.onSearch();
    }
  }

  onSearchLaw(): void {
    if (this.keywordsLaw.trim() === '') {
      Swal.fire({
        icon: 'warning',
        title: 'Warning',
        text: 'Please enter keywords to search.',
      });
      return;
    }
    const isFirstSearch = !this.searchResult1;

    const query: SearchQueryDTO = {
        keywords:  this.keywordsLaw.split(' ').filter(keyword => keyword.trim() !== '')
    };

    console.log(query);
    this.searchService.searchLawAdvanced(query, this.currentPage, this.pageSize).subscribe(
      result => {
        this.searchResult1 = result;
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

  onPageChange1(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      console.log(this.totalPages);
      this.currentPage = page;
      this.onSearchLaw();
    }
  }

}
