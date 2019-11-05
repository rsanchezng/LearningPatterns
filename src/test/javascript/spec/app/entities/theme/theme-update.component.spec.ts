import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LearningPatternsTestModule } from '../../../test.module';
import { ThemeUpdateComponent } from 'app/entities/theme/theme-update.component';
import { ThemeService } from 'app/entities/theme/theme.service';
import { Theme } from 'app/shared/model/theme.model';

describe('Component Tests', () => {
  describe('Theme Management Update Component', () => {
    let comp: ThemeUpdateComponent;
    let fixture: ComponentFixture<ThemeUpdateComponent>;
    let service: ThemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [ThemeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ThemeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThemeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ThemeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Theme(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Theme();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
