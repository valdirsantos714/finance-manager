import { NgModule } from '@angular/core';
import { ServerModule } from '@angular/platform-server';

import { AppModule } from './app.module';
import { AppComponent } from './app.component';
import { DespesaDashboardModule } from './components/despesa-dashboard/despesa-dashboard.module';
import { RendaDashboardModule } from './components/renda-dashboard/renda-dashboard.module';

@NgModule({
  imports: [
    AppModule,
    ServerModule,
    DespesaDashboardModule,
    RendaDashboardModule
  ],
  bootstrap: [AppComponent],
})
export class AppServerModule {}
