/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookYourMovieTestModule } from '../../../test.module';
import { RowComponent } from 'app/entities/row/row.component';
import { RowService } from 'app/entities/row/row.service';
import { Row } from 'app/shared/model/row.model';

describe('Component Tests', () => {
    describe('Row Management Component', () => {
        let comp: RowComponent;
        let fixture: ComponentFixture<RowComponent>;
        let service: RowService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [RowComponent],
                providers: []
            })
                .overrideTemplate(RowComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RowComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RowService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Row(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.rows[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
