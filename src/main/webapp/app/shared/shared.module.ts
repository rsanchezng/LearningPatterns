import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { LearningPatternsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [LearningPatternsSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [LearningPatternsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearningPatternsSharedModule {
  static forRoot() {
    return {
      ngModule: LearningPatternsSharedModule
    };
  }
}
