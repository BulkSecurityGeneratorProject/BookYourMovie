import { element, by, ElementFinder } from 'protractor';

export class RowComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-row div table .btn-danger'));
    title = element.all(by.css('jhi-row div h2#page-heading span')).first();

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

export class RowUpdatePage {
    pageTitle = element(by.id('jhi-row-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    serialNumberInput = element(by.id('field_serialNumber'));
    startPosInput = element(by.id('field_startPos'));
    priceInput = element(by.id('field_price'));
    nameInput = element(by.id('field_name'));
    seatSelect = element(by.id('field_seat'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setSerialNumberInput(serialNumber) {
        await this.serialNumberInput.sendKeys(serialNumber);
    }

    async getSerialNumberInput() {
        return this.serialNumberInput.getAttribute('value');
    }

    async setStartPosInput(startPos) {
        await this.startPosInput.sendKeys(startPos);
    }

    async getStartPosInput() {
        return this.startPosInput.getAttribute('value');
    }

    async setPriceInput(price) {
        await this.priceInput.sendKeys(price);
    }

    async getPriceInput() {
        return this.priceInput.getAttribute('value');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async seatSelectLastOption() {
        await this.seatSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async seatSelectOption(option) {
        await this.seatSelect.sendKeys(option);
    }

    getSeatSelect(): ElementFinder {
        return this.seatSelect;
    }

    async getSeatSelectedOption() {
        return this.seatSelect.element(by.css('option:checked')).getText();
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

export class RowDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-row-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-row'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
