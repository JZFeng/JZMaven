package com.jz.multiThread.async;

import java.util.concurrent.CompletableFuture;

public class Entry {
    public static void main(String[] args) {
        BrandService brandService = new BrandService();
        CategoryService categoryService = new CategoryService();
        SpuDetailsService spuDetailsService = new SpuDetailsService();
        SpuBo spuBo = new SpuBo();

        spuBo.setSpuId(1000L);
        System.out.println(spuBo);


        CompletableFuture.allOf(
                brandService.getBrandsAsync().thenAccept(spuBo::setBrands),
                categoryService.getCategoriesAsync().thenAcceptAsync(spuBo::setCategories),
                spuDetailsService.getSpuDetailsByID(spuBo.getSpuId()).thenAccept(spuBo::setSpuDetails)
        ).join();

        System.out.println(spuBo);
    }
}
