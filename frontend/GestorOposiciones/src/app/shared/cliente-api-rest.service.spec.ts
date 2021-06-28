import { TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';

import { ClienteApiRestService } from './cliente-api-rest.service';

describe('ClienteApiRestService', () => {
  let service: ClienteApiRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [HttpClient]
    });
    service = TestBed.inject(ClienteApiRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
