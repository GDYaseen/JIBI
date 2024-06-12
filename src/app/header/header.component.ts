import { Component, OnInit } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
import { TranslateService } from '@ngx-translate/core';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {

  langs = ['en', 'fr'];
  selected = 'en';
  constructor(
    public translate: TranslateService,
  ) {
    translate.addLangs(this.langs);
    translate.setDefaultLang('en');
    const browserLang = this.translate.getBrowserLang();
    this.translate.use(browserLang?.match(/en|fr/) ? browserLang : 'en');
  }
  ngOnInit() {
  }

  OnSelect(langSelect: MatSelectChange) {
    this.translate.use(langSelect.value);
  }
}
