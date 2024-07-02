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

  export interface Query {
    //keyword: any;
    field: string;
    operator: string;
    value: string;
    logicalOperator: string;
  }

  export interface QueryLaw {
    keyword: string;
    logicalOperator?: string;
  }