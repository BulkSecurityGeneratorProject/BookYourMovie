import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRow } from 'app/shared/model/row.model';

type EntityResponseType = HttpResponse<IRow>;
type EntityArrayResponseType = HttpResponse<IRow[]>;

@Injectable({ providedIn: 'root' })
export class RowService {
    public resourceUrl = SERVER_API_URL + 'api/rows';

    constructor(private http: HttpClient) {}

    create(row: IRow): Observable<EntityResponseType> {
        return this.http.post<IRow>(this.resourceUrl, row, { observe: 'response' });
    }

    update(row: IRow): Observable<EntityResponseType> {
        return this.http.put<IRow>(this.resourceUrl, row, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRow>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRow[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
