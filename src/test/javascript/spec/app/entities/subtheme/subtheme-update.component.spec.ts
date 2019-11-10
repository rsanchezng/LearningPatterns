/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { LearningPatternsTestModule } from '../../../test.module';
import { SubthemeUpdateComponent } from 'app/entities/subtheme/subtheme-update.component';
import { SubthemeService } from 'app/entities/subtheme/subtheme.service';
import { Subtheme } from 'app/shared/model/subtheme.model';

describe('Component Tests', () => {
  describe('Subtheme Management Update Component', () => {
    let comp: SubthemeUpdateComponent;
    let fixture: ComponentFixture<SubthemeUpdateComponent>;
    let service: SubthemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [SubthemeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubthemeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubthemeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubthemeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Subtheme(123);
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
        const entity = new Subtheme();
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
