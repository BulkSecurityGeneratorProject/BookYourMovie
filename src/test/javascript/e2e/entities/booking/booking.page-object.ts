import { element, by, ElementFinder } from 'protractor';

export class BookingComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-booking div table .btn-danger'));
    title = element.all(by.css('jhi-booking div h2#page-heading span')).first();

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

export class BookingUpdatePage {
    pageTitle = element(by.id('jhi-booking-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    timeInput = element(by.id('field_time'));
    showSelect = element(by.id('field_show'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setTimeInput(time) {
        await this.timeInput.sendKeys(time);
    }

    async getTimeInput() {
        return this.timeInput.getAttribute('value');
    }

    async showSelectLastOption() {
        await this.showSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async showSelectOption(option) {
        await this.showSelect.sendKeys(option);
    }

    getShowSelect(): ElementFinder {
        return this.showSelect;
    }

    async getShowSelectedOption() {
        return this.showSelect.element(by.css('option:checked')).getText();
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

export class BookingDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-booking-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-booking'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
