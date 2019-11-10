/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LearningPatternsTestModule } from '../../../test.module';
import { SubthemeDetailComponent } from 'app/entities/subtheme/subtheme-detail.component';
import { Subtheme } from 'app/shared/model/subtheme.model';

describe('Component Tests', () => {
  describe('Subtheme Management Detail Component', () => {
    let comp: SubthemeDetailComponent;
    let fixture: ComponentFixture<SubthemeDetailComponent>;
    const route = ({ data: of({ subtheme: new Subtheme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearningPatternsTestModule],
        declarations: [SubthemeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubthemeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubthemeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subtheme).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
