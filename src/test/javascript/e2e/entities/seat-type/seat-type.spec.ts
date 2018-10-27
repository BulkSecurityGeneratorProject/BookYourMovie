/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SeatTypeComponentsPage, SeatTypeDeleteDialog, SeatTypeUpdatePage } from './seat-type.page-object';

const expect = chai.expect;

describe('SeatType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let seatTypeUpdatePage: SeatTypeUpdatePage;
    let seatTypeComponentsPage: SeatTypeComponentsPage;
    let seatTypeDeleteDialog: SeatTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SeatTypes', async () => {
        await navBarPage.goToEntity('seat-type');
        seatTypeComponentsPage = new SeatTypeComponentsPage();
        expect(await seatTypeComponentsPage.getTitle()).to.eq('Seat Types');
    });

    it('should load create SeatType page', async () => {
        await seatTypeComponentsPage.clickOnCreateButton();
        seatTypeUpdatePage = new SeatTypeUpdatePage();
        expect(await seatTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Seat Type');
        await seatTypeUpdatePage.cancel();
    });

    it('should create and save SeatTypes', async () => {
        const nbButtonsBeforeCreate = await seatTypeComponentsPage.countDeleteButtons();

        await seatTypeComponentsPage.clickOnCreateButton();
        await promise.all([
            seatTypeUpdatePage.typeSelectLastOption(),
            seatTypeUpdatePage.setPriceInput('price'),
            seatTypeUpdatePage.screenSelectLastOption()
        ]);
        expect(await seatTypeUpdatePage.getPriceInput()).to.eq('price');
        await seatTypeUpdatePage.save();
        expect(await seatTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await seatTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SeatType', async () => {
        const nbButtonsBeforeDelete = await seatTypeComponentsPage.countDeleteButtons();
        await seatTypeComponentsPage.clickOnLastDeleteButton();

        seatTypeDeleteDialog = new SeatTypeDeleteDialog();
        expect(await seatTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Seat Type?');
        await seatTypeDeleteDialog.clickOnConfirmButton();

        expect(await seatTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
