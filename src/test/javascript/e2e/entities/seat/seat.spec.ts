/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SeatComponentsPage, SeatDeleteDialog, SeatUpdatePage } from './seat.page-object';

const expect = chai.expect;

describe('Seat e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let seatUpdatePage: SeatUpdatePage;
    let seatComponentsPage: SeatComponentsPage;
    let seatDeleteDialog: SeatDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Seats', async () => {
        await navBarPage.goToEntity('seat');
        seatComponentsPage = new SeatComponentsPage();
        expect(await seatComponentsPage.getTitle()).to.eq('Seats');
    });

    it('should load create Seat page', async () => {
        await seatComponentsPage.clickOnCreateButton();
        seatUpdatePage = new SeatUpdatePage();
        expect(await seatUpdatePage.getPageTitle()).to.eq('Create or edit a Seat');
        await seatUpdatePage.cancel();
    });

    it('should create and save Seats', async () => {
        const nbButtonsBeforeCreate = await seatComponentsPage.countDeleteButtons();

        await seatComponentsPage.clickOnCreateButton();
        await promise.all([
            seatUpdatePage.setSeatNumberInput('seatNumber'),
            seatUpdatePage.statusSelectLastOption(),
            seatUpdatePage.bookingSelectLastOption(),
            seatUpdatePage.seatTypeSelectLastOption()
        ]);
        expect(await seatUpdatePage.getSeatNumberInput()).to.eq('seatNumber');
        await seatUpdatePage.save();
        expect(await seatUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await seatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Seat', async () => {
        const nbButtonsBeforeDelete = await seatComponentsPage.countDeleteButtons();
        await seatComponentsPage.clickOnLastDeleteButton();

        seatDeleteDialog = new SeatDeleteDialog();
        expect(await seatDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Seat?');
        await seatDeleteDialog.clickOnConfirmButton();

        expect(await seatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
