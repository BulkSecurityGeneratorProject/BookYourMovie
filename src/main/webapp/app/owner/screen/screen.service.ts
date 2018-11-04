import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IScreen } from 'app/shared/model/screen.model';

type EntityResponseType = HttpResponse<IScreen>;
type EntityArrayResponseType = HttpResponse<IScreen[]>;

@Injectable({ providedIn: 'root' })
export class ScreenService {
    public resourceUrl = SERVER_API_URL + 'api/screens';

    constructor(private http: HttpClient) {}

    create(screen: IScreen): Observable<EntityResponseType> {
        return this.http.post<IScreen>(this.resourceUrl, screen, { observe: 'response' });
    }

    update(screen: IScreen): Observable<EntityResponseType> {
        return this.http.put<IScreen>(this.resourceUrl, screen, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IScreen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IScreen[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
