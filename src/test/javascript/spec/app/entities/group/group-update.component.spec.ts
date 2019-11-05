import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LearningPatternsTestModule } from '../../../test.module';
import { GroupUpdateComponent } from 'app/entities/group/group-update.component';
import { GroupService } from 'app/entities/group/group.service';
import { Group } from 'app/shared/model/group.model';

describe('Component Tests', () => {
  describe('Group Management Update Component', () => {
    let comp: GroupUpdateComponent;
    let fixture: ComponentFixture<GroupUpdateComponent>;
    let service: GroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [GroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Group(123);
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
        const entity = new Group();
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
