import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearningPatternsSharedModule } from 'app/shared/shared.module';
import { ThemeComponent } from './theme.component';
import { ThemeDetailComponent } from './theme-detail.component';
import { ThemeUpdateComponent } from './theme-update.component';
import { ThemeDeletePopupComponent, ThemeDeleteDialogComponent } from './theme-delete-dialog.component';
import { themeRoute, themePopupRoute } from './theme.route';

const ENTITY_STATES = [...themeRoute, ...themePopupRoute];

@NgModule({
  imports: [LearningPatternsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ThemeComponent, ThemeDetailComponent, ThemeUpdateComponent, ThemeDeleteDialogComponent, ThemeDeletePopupComponent],
  entryComponents: [ThemeDeleteDialogComponent]
})
export class LearningPatternsThemeModule {}
