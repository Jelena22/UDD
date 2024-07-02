import { Component, OnInit } from '@angular/core';
import { Query, QueryLaw } from 'src/app/model/user.model';
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
  pageSize: number = 2;
  keywords1: string = '';
  searchResult1: SearchResult | null = null;
  keywordsLaw: string = '';
  logicalOperator: string = 'AND';
  queries1: QueryLaw[] = [{ keyword: '' }];

  queries: Query[] = [
    { field: '', operator: ':', value: '', logicalOperator: 'AND' }
  ];

  constructor(private searchService: SearchService) {}

  ngOnInit(): void {
   
  }


  addQuery() {
    this.queries.push({ field: '', operator: ':', value: '', logicalOperator: 'AND' });
  }

  removeQuery(index: number) {
    this.queries.splice(index, 1);
  }

 /* onSearch(): void {
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
  }*/

  onSearch() {
      const formattedQuery = this.queries.reduce((acc: string[], query, index) => {
        const queryString = `${query.field}${query.operator}${query.value}`;
        if (index < this.queries.length - 1) {
          return [...acc, queryString, query.logicalOperator];
        } else {
          return [...acc, queryString];
        }
      }, []);
      const isFirstSearch = !this.searchResult;

      const query: SearchQueryDTO = {
        keywords: formattedQuery
      };
  
      this.searchService.searchContractAdvanced(query, this.currentPage, this.pageSize).subscribe(
        result => {
          this.searchResult = result;
          this.totalPages = result.totalPages;
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

  /*onSearchLaw(): void {
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
  }*/

  addQuery1(): void {
    this.queries1.push({ keyword: '' });
  }
  removeQuery1(index: number): void {
    this.queries1.splice(index, 1);
  }
  
  onSearchLaw(): void {
    const isFirstSearch = !this.searchResult1;
    const formattedQuery: string[] = [];
    this.queries1.forEach((query, index) => {
      // Dodavanje ključne reči u formatu "content:ključna_reč"
      if (query.keyword.trim() !== '') {
        formattedQuery.push(`content:${query.keyword}`);
      }

      // Dodavanje logičkog operatora ako postoji i nije poslednji element
      if (query.logicalOperator && index < this.queries1.length - 1) {
        formattedQuery.push(query.logicalOperator);
      }
    });

    // Formiranje objekta za pretragu sa ključnim rečima i logičkim operatorima
    const searchQuery: SearchQueryDTO = {
      keywords: formattedQuery
    };

    console.log(searchQuery); // Prikaz za debagiranje

    // Slanje upita na backend
    this.searchService.searchLawAdvanced(searchQuery, this.currentPage, this.pageSize).subscribe(
      result => {
        // Obrada rezultata
        this.searchResult1 = result;
          this.totalPages = result.totalPages;
          this.totalElements = this.searchResult1.totalElements;
        console.log('Rezultati pretrage:', result);
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
        console.error('Greška prilikom pretrage:', error);
        // Prikaz poruke o grešci korisniku
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
