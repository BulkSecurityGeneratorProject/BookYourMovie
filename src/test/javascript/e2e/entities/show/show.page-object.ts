import { element, by, ElementFinder } from 'protractor';

export class ShowComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-show div table .btn-danger'));
    title = element.all(by.css('jhi-show div h2#page-heading span')).first();

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

export class ShowUpdatePage {
    pageTitle = element(by.id('jhi-show-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    timeInput = element(by.id('field_time'));
    screenSelect = element(by.id('field_screen'));
    movieSelect = element(by.id('field_movie'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setTimeInput(time) {
        await this.timeInput.sendKeys(time);
    }

    async getTimeInput() {
        return this.timeInput.getAttribute('value');
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

    async movieSelectLastOption() {
        await this.movieSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async movieSelectOption(option) {
        await this.movieSelect.sendKeys(option);
    }

    getMovieSelect(): ElementFinder {
        return this.movieSelect;
    }

    async getMovieSelectedOption() {
        return this.movieSelect.element(by.css('option:checked')).getText();
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

export class ShowDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-show-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-show'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
