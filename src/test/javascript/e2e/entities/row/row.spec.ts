/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RowComponentsPage, RowDeleteDialog, RowUpdatePage } from './row.page-object';

const expect = chai.expect;

describe('Row e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let rowUpdatePage: RowUpdatePage;
    let rowComponentsPage: RowComponentsPage;
    let rowDeleteDialog: RowDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Rows', async () => {
        await navBarPage.goToEntity('row');
        rowComponentsPage = new RowComponentsPage();
        expect(await rowComponentsPage.getTitle()).to.eq('Rows');
    });

    it('should load create Row page', async () => {
        await rowComponentsPage.clickOnCreateButton();
        rowUpdatePage = new RowUpdatePage();
        expect(await rowUpdatePage.getPageTitle()).to.eq('Create or edit a Row');
        await rowUpdatePage.cancel();
    });

    it('should create and save Rows', async () => {
        const nbButtonsBeforeCreate = await rowComponentsPage.countDeleteButtons();

        await rowComponentsPage.clickOnCreateButton();
        await promise.all([
            rowUpdatePage.setSerialNumberInput('5'),
            rowUpdatePage.setStartPosInput('5'),
            rowUpdatePage.setPriceInput('price'),
            rowUpdatePage.setNameInput('name'),
            rowUpdatePage.seatSelectLastOption()
        ]);
        expect(await rowUpdatePage.getSerialNumberInput()).to.eq('5');
        expect(await rowUpdatePage.getStartPosInput()).to.eq('5');
        expect(await rowUpdatePage.getPriceInput()).to.eq('price');
        expect(await rowUpdatePage.getNameInput()).to.eq('name');
        await rowUpdatePage.save();
        expect(await rowUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await rowComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Row', async () => {
        const nbButtonsBeforeDelete = await rowComponentsPage.countDeleteButtons();
        await rowComponentsPage.clickOnLastDeleteButton();

        rowDeleteDialog = new RowDeleteDialog();
        expect(await rowDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Row?');
        await rowDeleteDialog.clickOnConfirmButton();

        expect(await rowComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
