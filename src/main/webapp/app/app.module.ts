import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { LearningPatternsSharedModule } from 'app/shared/shared.module';
import { LearningPatternsCoreModule } from 'app/core/core.module';
import { LearningPatternsAppRoutingModule } from './app-routing.module';
import { LearningPatternsHomeModule } from './home/home.module';
import { LearningPatternsEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    LearningPatternsSharedModule,
    LearningPatternsCoreModule,
    LearningPatternsHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    LearningPatternsEntityModule,
    LearningPatternsAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class LearningPatternsAppModule {}
