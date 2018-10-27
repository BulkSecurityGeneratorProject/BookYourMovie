import { element, by, ElementFinder } from 'protractor';

export class TheatreComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-theatre div table .btn-danger'));
    title = element.all(by.css('jhi-theatre div h2#page-heading span')).first();

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

export class TheatreUpdatePage {
    pageTitle = element(by.id('jhi-theatre-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    areaInput = element(by.id('field_area'));
    citySelect = element(by.id('field_city'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setAreaInput(area) {
        await this.areaInput.sendKeys(area);
    }

    async getAreaInput() {
        return this.areaInput.getAttribute('value');
    }

    async citySelectLastOption() {
        await this.citySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async citySelectOption(option) {
        await this.citySelect.sendKeys(option);
    }

    getCitySelect(): ElementFinder {
        return this.citySelect;
    }

    async getCitySelectedOption() {
        return this.citySelect.element(by.css('option:checked')).getText();
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

export class TheatreDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-theatre-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-theatre'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
