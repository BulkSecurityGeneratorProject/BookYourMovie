/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookYourMovieTestModule } from '../../../test.module';
import { TheatreDetailComponent } from 'app/entities/theatre/theatre-detail.component';
import { Theatre } from 'app/shared/model/theatre.model';

describe('Component Tests', () => {
    describe('Theatre Management Detail Component', () => {
        let comp: TheatreDetailComponent;
        let fixture: ComponentFixture<TheatreDetailComponent>;
        const route = ({ data: of({ theatre: new Theatre(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BookYourMovieTestModule],
                declarations: [TheatreDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TheatreDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TheatreDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.theatre).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
