/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookYourMovieTestModule } from '../../../test.module';
import { ShowDetailComponent } from 'app/entities/show/show-detail.component';
import { Show } from 'app/shared/model/show.model';

describe('Component Tests', () => {
    describe('Show Management Detail Component', () => {
        let comp: ShowDetailComponent;
        let fixture: ComponentFixture<ShowDetailComponent>;
        const route = ({ data: of({ show: new Show(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [ShowDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ShowDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ShowDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.show).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
