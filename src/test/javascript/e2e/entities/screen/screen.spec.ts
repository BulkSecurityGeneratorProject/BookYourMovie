/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ScreenComponentsPage, ScreenDeleteDialog, ScreenUpdatePage } from './screen.page-object';

const expect = chai.expect;

describe('Screen e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let screenUpdatePage: ScreenUpdatePage;
    let screenComponentsPage: ScreenComponentsPage;
    let screenDeleteDialog: ScreenDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Screens', async () => {
        await navBarPage.goToEntity('screen');
        screenComponentsPage = new ScreenComponentsPage();
        expect(await screenComponentsPage.getTitle()).to.eq('Screens');
    });

    it('should load create Screen page', async () => {
        await screenComponentsPage.clickOnCreateButton();
        screenUpdatePage = new ScreenUpdatePage();
        expect(await screenUpdatePage.getPageTitle()).to.eq('Create or edit a Screen');
        await screenUpdatePage.cancel();
    });

    it('should create and save Screens', async () => {
        const nbButtonsBeforeCreate = await screenComponentsPage.countDeleteButtons();

        await screenComponentsPage.clickOnCreateButton();
        await promise.all([screenUpdatePage.setNameInput('name'), screenUpdatePage.theatreSelectLastOption()]);
        expect(await screenUpdatePage.getNameInput()).to.eq('name');
        await screenUpdatePage.save();
        expect(await screenUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await screenComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Screen', async () => {
        const nbButtonsBeforeDelete = await screenComponentsPage.countDeleteButtons();
        await screenComponentsPage.clickOnLastDeleteButton();

        screenDeleteDialog = new ScreenDeleteDialog();
        expect(await screenDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Screen?');
        await screenDeleteDialog.clickOnConfirmButton();

        expect(await screenComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
