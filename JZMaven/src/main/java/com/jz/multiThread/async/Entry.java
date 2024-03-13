package com.jz.multiThread.async;

public class Entry {
    public static void main(String[] args) {
        BrandService brandService = new BrandService();
        CategoryService categoryService = new CategoryService();
        SpuBo spuBo = new SpuBo();

        spuBo.setSpuId(1000L);
        System.out.println(spuBo);
        brandService.getBrandsAsync().thenAccept(spuBo::setBrands).join();
        categoryService.getCategoriesAsync().thenAccept(spuBo::setCategories).join();

        System.out.println(spuBo);


    }
}
