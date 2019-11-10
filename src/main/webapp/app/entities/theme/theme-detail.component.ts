import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITheme } from 'app/shared/model/theme.model';

@Component({
  selector: 'jhi-theme-detail',
  templateUrl: './theme-detail.component.html'
})
export class ThemeDetailComponent implements OnInit {
  theme: ITheme;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ theme }) => {
      this.theme = theme;
    });
  }

  previousState() {
    window.history.back();
  }
}
