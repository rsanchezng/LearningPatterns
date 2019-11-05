import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearningPatternsTestModule } from '../../../test.module';
import { ThemeComponent } from 'app/entities/theme/theme.component';
import { ThemeService } from 'app/entities/theme/theme.service';
import { Theme } from 'app/shared/model/theme.model';

describe('Component Tests', () => {
  describe('Theme Management Component', () => {
    let comp: ThemeComponent;
    let fixture: ComponentFixture<ThemeComponent>;
    let service: ThemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [ThemeComponent],
        providers: []
      })
        .overrideTemplate(ThemeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThemeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Theme(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.themes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
