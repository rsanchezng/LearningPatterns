import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearningPatternsTestModule } from '../../../test.module';
import { SubthemeComponent } from 'app/entities/subtheme/subtheme.component';
import { SubthemeService } from 'app/entities/subtheme/subtheme.service';
import { Subtheme } from 'app/shared/model/subtheme.model';

describe('Component Tests', () => {
  describe('Subtheme Management Component', () => {
    let comp: SubthemeComponent;
    let fixture: ComponentFixture<SubthemeComponent>;
    let service: SubthemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [SubthemeComponent],
        providers: []
      })
        .overrideTemplate(SubthemeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubthemeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubthemeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Subtheme(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subthemes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
