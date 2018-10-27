import { element, by, ElementFinder } from 'protractor';

export class MovieComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-movie div table .btn-danger'));
    title = element.all(by.css('jhi-movie div h2#page-heading span')).first();

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

export class MovieUpdatePage {
    pageTitle = element(by.id('jhi-movie-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    languageSelect = element(by.id('field_language'));
    descriptionInput = element(by.id('field_description'));
    imageUrlInput = element(by.id('field_imageUrl'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setLanguageSelect(language) {
        await this.languageSelect.sendKeys(language);
    }

    async getLanguageSelect() {
        return this.languageSelect.element(by.css('option:checked')).getText();
    }

    async languageSelectLastOption() {
        await this.languageSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setImageUrlInput(imageUrl) {
        await this.imageUrlInput.sendKeys(imageUrl);
    }

    async getImageUrlInput() {
        return this.imageUrlInput.getAttribute('value');
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

export class MovieDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-movie-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-movie'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
