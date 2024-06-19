import {HttpHeaders} from "@angular/common/http";

export class Constant {
  static readonly API_URL = 'http://127.0.0.1:8080';

  static readonly JSON_HEADERS = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Accept', 'application/json');
}
