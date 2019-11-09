import { NgModule } from '@angular/core';

import { LearningPatternsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [LearningPatternsSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [LearningPatternsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class LearningPatternsSharedCommonModule {}
