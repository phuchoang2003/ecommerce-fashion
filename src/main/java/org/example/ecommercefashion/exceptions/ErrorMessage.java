package org.example.ecommercefashion.exceptions;

import com.longnh.exceptions.BaseErrorMessage;

public enum ErrorMessage implements BaseErrorMessage {
    SUCCESS("Success"),
    FALSE("False"),
    ROLE_NOT_FOUND("Role not found"),
    PERMISSION_NOT_FOUND("Permission not found"),
    USER_NOT_FOUND("User not found"),
    ROLE_ALREADY_ASSIGNED("Role already assigned"),
    REFRESH_TOKEN_NOT_FOUND("Refresh token not found"),
    BAD_CREDENTIAL("Bad credential"),
    INVALID_REFRESH_TOKEN("Invalid refresh token"),
    CURRENT_PASSWORD_SAME_NEW_PASSWORD("Current password same new password"),
    OTP_IS_EXPIRED("OTP is expired"),
    ERROR_UPLOADING_FILE("Error uploading file"),
    SOMETHING_WENT_WRONG("Something went wrong"),
    NOT_FOUND_BUCKET("Not found bucket"),
    IMAGE_NOT_FOUND("Image not found"),
    UNAUTHORIZED("Unauthorized"),
    NOT_FOUND_CATEGORY("Not found category"),
    FILES_TOO_LARGE("Files too large"),
    SIZE_CHART_NOT_FOUND("Size chart not found"),
    SIZE_CHART_IDS_NOT_FOUND("There is a certain id not found in the list ids"),
    FILE_NOT_FOUND("File not found"),
    INVALID_FILE_TYPE("We only accept jpeg, png, and svg file types"),
    ONLY_ONE_FILE_ALLOWED("Only one file allowed"),
    DUPLICATE_CATEGORY("Duplicate category"),
    ATTRIBUTE_NOT_FOUND("Attribute not found"),
    ATTRIBUTE_NAME_DUPLICATE("Attribute name is already exists"),
    ATTRIBUTE_DISPLAY_NAME_DUPLICATE("Attribute display name is already exists"),
    ATTRIBUTE_VALUE_NOT_FOUND("Attribute value not found"),
    ATTRIBUTE_VALUE_DUPLICATE("Attribute value is already exists"),
    ATTRIBUTE_DISPLAY_VALUE_DUPLICATE("Attribute display value is already exists"),
    UNIT_DUPLICATE("Unit is already exists"),

    UNIT_NOT_FOUND("Unit not found"),
    DISPLAY_UNIT_DUPLICATE("Display unit is already exists"),
    INVALID_PASSWORD("Invalid password"),
    RESET_TOKEN_NOT_FOUND("Reset token not found"),
    ATTRIBUTE_IS_MANDATORY("Attribute is mandatory"),
    PRODUCT_NOT_FOUND("Product not found"),
    ATTRIBUTE_IDS_NOT_FOUND("There is a certain id not found in the list ids"),
    ONLY_ALLOW_ONE_ATTRIBUTE_CORESPONDING_ATTRIBUTE_VALUE("Only allow one attribute corresponding attribute value"),
    CATEGORY_NOT_SUPPORT_SIZE_CHART("Category not support size chart"),
    CATEGORY_ONLY_SUPPORT_SIZE_CHART("Category only support size chart"),
    CART_NOT_FOUND("Cart not found"),
    PRODUCT_VARIANT_NOT_FOUND("Product variant not found"),
    CART_ITEM_NOT_FOUND("Cart item not found"),
    ;

    public String val;

    private ErrorMessage(String label) {
        val = label;
    }

    @Override
    public String val() {
        return val;
    }
}
