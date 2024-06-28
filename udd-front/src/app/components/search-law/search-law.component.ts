import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FileService } from 'src/app/service/file.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-search-law',
  templateUrl: './search-law.component.html',
  styleUrls: ['./search-law.component.css']
})
export class SearchLawComponent implements OnInit{

  files: File[] = [];


  constructor( private router: Router,
    private fileService: FileService) {}

  ngOnInit(): void {
   
  }

  onFileSelected(event: any){
    if (event.target.files) {
      this.files = Array.from(event.target.files);
    }
  }

  onUpload(){
    if (this.files.length > 0) {
      this.fileService.uploadFiles(this.files).subscribe(
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

}
