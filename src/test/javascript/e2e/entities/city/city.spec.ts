/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CityComponentsPage, CityDeleteDialog, CityUpdatePage } from './city.page-object';

const expect = chai.expect;

describe('City e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let cityUpdatePage: CityUpdatePage;
    let cityComponentsPage: CityComponentsPage;
    let cityDeleteDialog: CityDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Cities', async () => {
        await navBarPage.goToEntity('city');
        cityComponentsPage = new CityComponentsPage();
        expect(await cityComponentsPage.getTitle()).to.eq('Cities');
    });

    it('should load create City page', async () => {
        await cityComponentsPage.clickOnCreateButton();
        cityUpdatePage = new CityUpdatePage();
        expect(await cityUpdatePage.getPageTitle()).to.eq('Create or edit a City');
        await cityUpdatePage.cancel();
    });

    it('should create and save Cities', async () => {
        const nbButtonsBeforeCreate = await cityComponentsPage.countDeleteButtons();

        await cityComponentsPage.clickOnCreateButton();
        await promise.all([cityUpdatePage.nameSelectLastOption()]);
        await cityUpdatePage.save();
        expect(await cityUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await cityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last City', async () => {
        const nbButtonsBeforeDelete = await cityComponentsPage.countDeleteButtons();
        await cityComponentsPage.clickOnLastDeleteButton();

        cityDeleteDialog = new CityDeleteDialog();
        expect(await cityDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this City?');
        await cityDeleteDialog.clickOnConfirmButton();

        expect(await cityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
