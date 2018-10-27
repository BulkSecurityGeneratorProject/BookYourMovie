import { element, by, ElementFinder } from 'protractor';

export class ScreenComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-screen div table .btn-danger'));
    title = element.all(by.css('jhi-screen div h2#page-heading span')).first();

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

export class ScreenUpdatePage {
    pageTitle = element(by.id('jhi-screen-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    theatreSelect = element(by.id('field_theatre'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async theatreSelectLastOption() {
        await this.theatreSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async theatreSelectOption(option) {
        await this.theatreSelect.sendKeys(option);
    }

    getTheatreSelect(): ElementFinder {
        return this.theatreSelect;
    }

    async getTheatreSelectedOption() {
        return this.theatreSelect.element(by.css('option:checked')).getText();
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

export class ScreenDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-screen-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-screen'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
