import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubtheme } from 'app/shared/model/subtheme.model';

@Component({
  selector: 'jhi-subtheme-detail',
  templateUrl: './subtheme-detail.component.html'
})
export class SubthemeDetailComponent implements OnInit {
  subtheme: ISubtheme;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subtheme }) => {
      this.subtheme = subtheme;
    });
  }

  previousState() {
    window.history.back();
  }
}
