export class User {
    constructor(
        public email: string = '',
        public password: string = '',
        public passwordConfirm: string = '',
        public name: string = '',
        public surname: string = '',  
      ) {}
}

export class AuthRequest {
    constructor(public email: string = '', public password: string = '') {}
  }
