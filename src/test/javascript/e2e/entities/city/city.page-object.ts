import { element, by, ElementFinder } from 'protractor';

export class CityComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-city div table .btn-danger'));
    title = element.all(by.css('jhi-city div h2#page-heading span')).first();

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

export class CityUpdatePage {
    pageTitle = element(by.id('jhi-city-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameSelect = element(by.id('field_name'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameSelect(name) {
        await this.nameSelect.sendKeys(name);
    }

    async getNameSelect() {
        return this.nameSelect.element(by.css('option:checked')).getText();
    }

    async nameSelectLastOption() {
        await this.nameSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

export class CityDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-city-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-city'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
