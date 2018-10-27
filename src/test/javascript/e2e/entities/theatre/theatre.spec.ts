/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TheatreComponentsPage, TheatreDeleteDialog, TheatreUpdatePage } from './theatre.page-object';

const expect = chai.expect;

describe('Theatre e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let theatreUpdatePage: TheatreUpdatePage;
    let theatreComponentsPage: TheatreComponentsPage;
    let theatreDeleteDialog: TheatreDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Theatres', async () => {
        await navBarPage.goToEntity('theatre');
        theatreComponentsPage = new TheatreComponentsPage();
        expect(await theatreComponentsPage.getTitle()).to.eq('Theatres');
    });

    it('should load create Theatre page', async () => {
        await theatreComponentsPage.clickOnCreateButton();
        theatreUpdatePage = new TheatreUpdatePage();
        expect(await theatreUpdatePage.getPageTitle()).to.eq('Create or edit a Theatre');
        await theatreUpdatePage.cancel();
    });

    it('should create and save Theatres', async () => {
        const nbButtonsBeforeCreate = await theatreComponentsPage.countDeleteButtons();

        await theatreComponentsPage.clickOnCreateButton();
        await promise.all([
            theatreUpdatePage.setNameInput('name'),
            theatreUpdatePage.setAreaInput('area'),
            theatreUpdatePage.citySelectLastOption()
        ]);
        expect(await theatreUpdatePage.getNameInput()).to.eq('name');
        expect(await theatreUpdatePage.getAreaInput()).to.eq('area');
        await theatreUpdatePage.save();
        expect(await theatreUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await theatreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Theatre', async () => {
        const nbButtonsBeforeDelete = await theatreComponentsPage.countDeleteButtons();
        await theatreComponentsPage.clickOnLastDeleteButton();

        theatreDeleteDialog = new TheatreDeleteDialog();
        expect(await theatreDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Theatre?');
        await theatreDeleteDialog.clickOnConfirmButton();

        expect(await theatreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
