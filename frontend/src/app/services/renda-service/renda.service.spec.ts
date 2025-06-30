import { TestBed } from '@angular/core/testing';

import { RendaService } from './renda.service';

describe('RendaServiceService', () => {
  let service: RendaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RendaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
