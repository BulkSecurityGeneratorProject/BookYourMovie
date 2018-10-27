/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ShowComponentsPage, ShowDeleteDialog, ShowUpdatePage } from './show.page-object';

const expect = chai.expect;

describe('Show e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let showUpdatePage: ShowUpdatePage;
    let showComponentsPage: ShowComponentsPage;
    let showDeleteDialog: ShowDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Shows', async () => {
        await navBarPage.goToEntity('show');
        showComponentsPage = new ShowComponentsPage();
        expect(await showComponentsPage.getTitle()).to.eq('Shows');
    });

    it('should load create Show page', async () => {
        await showComponentsPage.clickOnCreateButton();
        showUpdatePage = new ShowUpdatePage();
        expect(await showUpdatePage.getPageTitle()).to.eq('Create or edit a Show');
        await showUpdatePage.cancel();
    });

    it('should create and save Shows', async () => {
        const nbButtonsBeforeCreate = await showComponentsPage.countDeleteButtons();

        await showComponentsPage.clickOnCreateButton();
        await promise.all([
            showUpdatePage.setTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            showUpdatePage.screenSelectLastOption(),
            showUpdatePage.movieSelectLastOption()
        ]);
        expect(await showUpdatePage.getTimeInput()).to.contain('2001-01-01T02:30');
        await showUpdatePage.save();
        expect(await showUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await showComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Show', async () => {
        const nbButtonsBeforeDelete = await showComponentsPage.countDeleteButtons();
        await showComponentsPage.clickOnLastDeleteButton();

        showDeleteDialog = new ShowDeleteDialog();
        expect(await showDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Show?');
        await showDeleteDialog.clickOnConfirmButton();

        expect(await showComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
