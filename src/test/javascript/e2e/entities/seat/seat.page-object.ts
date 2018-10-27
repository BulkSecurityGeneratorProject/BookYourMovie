import { element, by, ElementFinder } from 'protractor';

export class SeatComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-seat div table .btn-danger'));
    title = element.all(by.css('jhi-seat div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class SeatUpdatePage {
    pageTitle = element(by.id('jhi-seat-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    seatNumberInput = element(by.id('field_seatNumber'));
    statusSelect = element(by.id('field_status'));
    bookingSelect = element(by.id('field_booking'));
    seatTypeSelect = element(by.id('field_seatType'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setSeatNumberInput(seatNumber) {
        await this.seatNumberInput.sendKeys(seatNumber);
    }

    async getSeatNumberInput() {
        return this.seatNumberInput.getAttribute('value');
    }

    async setStatusSelect(status) {
        await this.statusSelect.sendKeys(status);
    }

    async getStatusSelect() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async bookingSelectLastOption() {
        await this.bookingSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async bookingSelectOption(option) {
        await this.bookingSelect.sendKeys(option);
    }

    getBookingSelect(): ElementFinder {
        return this.bookingSelect;
    }

    async getBookingSelectedOption() {
        return this.bookingSelect.element(by.css('option:checked')).getText();
    }

    async seatTypeSelectLastOption() {
        await this.seatTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async seatTypeSelectOption(option) {
        await this.seatTypeSelect.sendKeys(option);
    }

    getSeatTypeSelect(): ElementFinder {
        return this.seatTypeSelect;
    }

    async getSeatTypeSelectedOption() {
        return this.seatTypeSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class SeatDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-seat-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-seat'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
