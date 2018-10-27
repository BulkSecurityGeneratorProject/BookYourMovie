/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BookingComponentsPage, BookingDeleteDialog, BookingUpdatePage } from './booking.page-object';

const expect = chai.expect;

describe('Booking e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let bookingUpdatePage: BookingUpdatePage;
    let bookingComponentsPage: BookingComponentsPage;
    let bookingDeleteDialog: BookingDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Bookings', async () => {
        await navBarPage.goToEntity('booking');
        bookingComponentsPage = new BookingComponentsPage();
        expect(await bookingComponentsPage.getTitle()).to.eq('Bookings');
    });

    it('should load create Booking page', async () => {
        await bookingComponentsPage.clickOnCreateButton();
        bookingUpdatePage = new BookingUpdatePage();
        expect(await bookingUpdatePage.getPageTitle()).to.eq('Create or edit a Booking');
        await bookingUpdatePage.cancel();
    });

    it('should create and save Bookings', async () => {
        const nbButtonsBeforeCreate = await bookingComponentsPage.countDeleteButtons();

        await bookingComponentsPage.clickOnCreateButton();
        await promise.all([
            bookingUpdatePage.setTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            bookingUpdatePage.showSelectLastOption()
        ]);
        expect(await bookingUpdatePage.getTimeInput()).to.contain('2001-01-01T02:30');
        await bookingUpdatePage.save();
        expect(await bookingUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await bookingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Booking', async () => {
        const nbButtonsBeforeDelete = await bookingComponentsPage.countDeleteButtons();
        await bookingComponentsPage.clickOnLastDeleteButton();

        bookingDeleteDialog = new BookingDeleteDialog();
        expect(await bookingDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Booking?');
        await bookingDeleteDialog.clickOnConfirmButton();

        expect(await bookingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
