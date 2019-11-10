/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LearningPatternsTestModule } from '../../../test.module';
import { ActivityComponent } from 'app/entities/activity/activity.component';
import { ActivityService } from 'app/entities/activity/activity.service';
import { Activity } from 'app/shared/model/activity.model';

describe('Component Tests', () => {
  describe('Activity Management Component', () => {
    let comp: ActivityComponent;
    let fixture: ComponentFixture<ActivityComponent>;
    let service: ActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [ActivityComponent],
        providers: []
      })
        .overrideTemplate(ActivityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Activity(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.activities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
