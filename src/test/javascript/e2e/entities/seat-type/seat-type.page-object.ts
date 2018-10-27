import { element, by, ElementFinder } from 'protractor';

export class SeatTypeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-seat-type div table .btn-danger'));
    title = element.all(by.css('jhi-seat-type div h2#page-heading span')).first();

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

export class SeatTypeUpdatePage {
    pageTitle = element(by.id('jhi-seat-type-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    typeSelect = element(by.id('field_type'));
    priceInput = element(by.id('field_price'));
    screenSelect = element(by.id('field_screen'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setTypeSelect(type) {
        await this.typeSelect.sendKeys(type);
    }

    async getTypeSelect() {
        return this.typeSelect.element(by.css('option:checked')).getText();
    }

    async typeSelectLastOption() {
        await this.typeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setPriceInput(price) {
        await this.priceInput.sendKeys(price);
    }

    async getPriceInput() {
        return this.priceInput.getAttribute('value');
    }

    async screenSelectLastOption() {
        await this.screenSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async screenSelectOption(option) {
        await this.screenSelect.sendKeys(option);
    }

    getScreenSelect(): ElementFinder {
        return this.screenSelect;
    }

    async getScreenSelectedOption() {
        return this.screenSelect.element(by.css('option:checked')).getText();
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

export class SeatTypeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-seatType-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-seatType'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
