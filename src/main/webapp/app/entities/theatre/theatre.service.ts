import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITheatre } from 'app/shared/model/theatre.model';

type EntityResponseType = HttpResponse<ITheatre>;
type EntityArrayResponseType = HttpResponse<ITheatre[]>;

@Injectable({ providedIn: 'root' })
export class TheatreService {
    public resourceUrl = SERVER_API_URL + 'api/theatres';

    constructor(private http: HttpClient) {}

    create(theatre: ITheatre): Observable<EntityResponseType> {
        return this.http.post<ITheatre>(this.resourceUrl, theatre, { observe: 'response' });
    }

    update(theatre: ITheatre): Observable<EntityResponseType> {
        return this.http.put<ITheatre>(this.resourceUrl, theatre, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITheatre>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITheatre[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
