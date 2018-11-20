/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookYourMovieTestModule } from '../../../test.module';
import { RowDetailComponent } from 'app/entities/row/row-detail.component';
import { Row } from 'app/shared/model/row.model';

describe('Component Tests', () => {
    describe('Row Management Detail Component', () => {
        let comp: RowDetailComponent;
        let fixture: ComponentFixture<RowDetailComponent>;
        const route = ({ data: of({ row: new Row(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [RowDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RowDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RowDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.row).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
