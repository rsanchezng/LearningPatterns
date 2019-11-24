import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearningPatternsTestModule } from '../../../test.module';
import { StudentActivityComponent } from 'app/entities/student-activity/student-activity.component';
import { StudentActivityService } from 'app/entities/student-activity/student-activity.service';
import { StudentActivity } from 'app/shared/model/student-activity.model';

describe('Component Tests', () => {
  describe('StudentActivity Management Component', () => {
    let comp: StudentActivityComponent;
    let fixture: ComponentFixture<StudentActivityComponent>;
    let service: StudentActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [StudentActivityComponent],
        providers: []
      })
        .overrideTemplate(StudentActivityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudentActivityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudentActivityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StudentActivity(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.studentActivities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
