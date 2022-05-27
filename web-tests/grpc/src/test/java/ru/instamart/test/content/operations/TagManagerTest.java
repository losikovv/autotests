package ru.instamart.test.content.operations;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseId;
import tagmanager.TagManagerGrpc;
import tagmanager.Tagmanager;

import java.util.UUID;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Epic("Tag Manager Microservice")
@Feature("Tag Manager")
public class TagManagerTest extends GrpcBase {
    private TagManagerGrpc.TagManagerBlockingStub client;
    private final String ownerName = UUID.randomUUID().toString();
    private final String ownerDescription = "test description";
    private int ownerId;
    private int ownerIdMultiplyItems;
    private final String tagName = UUID.randomUUID().toString();
    private final String tagNameMultiplyItems = UUID.randomUUID().toString();
    private int tagId;
    private int tagIdMultiplyItems;
    private final String itemId = "something";

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_TAG_MANAGER);
        client = TagManagerGrpc.newBlockingStub(channel);
    }

    @Story("Владелец")
    @CaseId(3)
    @Test(description = "Создание владельца с валидными данными",
            groups = {"grpc-tag-manager"})
    public void createOwner() {
        var request = Tagmanager.CreateOwnerRequest
                .newBuilder()
                .setName(ownerName)
                .setDescription(ownerDescription)
                .build();

        var response = client.createOwner(request);

        check.id(response.getId());
        check.equals(response.getName(), ownerName);
        check.equals(response.getDescription(), ownerDescription);

        ownerId = response.getId();
    }

    @Story("Владелец")
    @CaseId(4)
    @Test(description = "Создание владельца с существующим именем",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createOwner",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "ALREADY_EXISTS: Owner with similar name already exists: " +
                    "\\{ID:\\d+ Name:[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12} Description:test description}")
    public void createDuplicateOwner() {
        var request = Tagmanager.CreateOwnerRequest
                .newBuilder()
                .setName(ownerName)
                .setDescription(ownerDescription)
                .build();

        client.createOwner(request);
    }

    @Story("Владелец")
    @CaseId(8)
    @Test(description = "Получение списка владельцев",
            groups = {"grpc-tag-manager"})
    public void getOwners() {
        var request = Tagmanager.GetOwnersRequest.newBuilder().build();

        var owners = client.getOwners(request).getOwnersList();

        for (var owner : owners) {
            check.id(owner.getId());
            check.notNull(owner.getName());
            check.notNull(owner.getDescription());
        }
    }

    @Story("Тэг")
    @CaseId(17)
    @Test(description = "Создание тега с валидными данными",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createOwner")
    public void createTag() {
        var request = Tagmanager.CreateTagRequest.newBuilder()
                .setName(tagName)
                .setOwnerId(ownerId)
                .build();

        var response = client.createTag(request);

        check.id(response.getId());
        check.equals(response.getName(), tagName);
        check.equals(response.getOwnerId(), ownerId);

        tagId = response.getId();
    }

    @Story("Тэг")
    @CaseId(18)
    @Test(description = "Создание тега с существующим именем",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createTag",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "ALREADY_EXISTS: Tag with similar name already exists: " +
                    "\\{ID:\\d+ Name:[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12} OwnerID:\\d+}")
    public void createDuplicateTag() {
        var request = Tagmanager.CreateTagRequest.newBuilder()
                .setName(tagName)
                .setOwnerId(ownerId)
                .build();

        client.createTag(request);
    }

    @Story("Тэг")
    @CaseId(24)
    @Test(description = "Создание тега с несуществующим id владельца",
            groups = {"grpc-tag-manager"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot create tag: ERROR: insert or update on table \"tags\" " +
                    "violates foreign key constraint \"tags_owner_id_fkey\" \\(SQLSTATE 23503\\)")
    public void createTagWithUnknownOwnerId() {
        var request = Tagmanager.CreateTagRequest.newBuilder()
                .setName(UUID.randomUUID().toString())
                .setOwnerId(Integer.MAX_VALUE)
                .build();

        client.createTag(request);
    }

    @Story("Тэг")
    @CaseId(31)
    @Test(description = "Удаление существующего тега",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "getTagsByOwner")
    public void deleteTag() {
        var request = Tagmanager.DeleteTagRequest.newBuilder()
                .setTagId(tagId)
                .build();

        var response = client.deleteTag(request);
        assertTrue(response.getOk());
    }

    @Story("Тэг")
    @CaseId(34)
    @Test(description = "Проверка, что с удаленным тегом удаляются все привязки тега",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "deleteTag")
    public void getBindsByItemDeletedTag() {
        var binds = grpc.getBindsByItem(ownerId, "something");

        check.equals(binds.size(), 0);
    }

    @Story("Тэг")
    @CaseId(26)
    @Test(description = "Получение списка тегов владельца",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createDuplicateTag")
    public void getTagsByOwner() {
        var request = Tagmanager.GetTagsByOwnerRequest.newBuilder()
                .setOwnerId(ownerId)
                .build();

        var tag = client.getTagsByOwner(request).getTags(0);

        check.id(tag.getId());
        check.equals(tag.getName(), tagName);
        check.equals(tag.getOwnerId(), ownerId);
    }

    @Story("Тэг")
    @CaseId(29)
    @Test(description = "Получение списка тегов владельца по несуществующему id владельца",
            groups = {"grpc-tag-manager"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot find owner by id")
    public void getTagsByUnknownOwner() {
        var request = Tagmanager.GetTagsByOwnerRequest.newBuilder()
                .setOwnerId(Integer.MAX_VALUE)
                .build();

        client.getTagsByOwner(request);
    }

    @Story("Тэг")
    @CaseId(55)
    @Test(description = "Получение списка привязок тега",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "bindTag")
    public void getBindsByTag() {
        var request = Tagmanager.GetBindsByTagRequest
                .newBuilder()
                .setTagId(tagId)
                .build();

        var bind = client.getBindsByTag(request).getBinds(0);

        check.equals(bind.getTagId(), tagId);
        check.equals(bind.getItemId(), itemId);
    }

    @Story("Тэг")
    @CaseId(148)
    @Test(description = "Получение списка привязанных тегов по владельцу и сущности",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "bindTag")
    public void getTagsByItem() {
        var request = Tagmanager.GetTagsByItemRequest.newBuilder()
                .setOwnerId(ownerId)
                .setItemId(itemId)
                .build();

        var tag = client.getTagsByItem(request).getTags(0);

        check.id(tag.getId());
        check.equals(tag.getName(), tagName);
        check.equals(tag.getOwnerId(), ownerId);
    }

    @Story("Тэг")
    @CaseId(152)
    @Test(description = "Получение списка тегов по сущности и несуществующему владельцу",
            groups = {"grpc-tag-manager"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot get tags list: check owner existense failed: entity not found")
    public void getTagsByItemOwnerNotExisted() {
        var request = Tagmanager.GetTagsByItemRequest.newBuilder()
                .setOwnerId(Integer.MAX_VALUE)
                .setItemId(itemId)
                .build();

        client.getTagsByItem(request);
    }

    @Story("Тэг")
    @CaseId(150)
    @Test(description = "Получение списка тегов по несуществующей сущности и владельцу",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "bindTag")
    public void getTagsByNonExistedItem() {
        var request = Tagmanager.GetTagsByItemRequest.newBuilder()
                .setOwnerId(ownerId)
                .setItemId("non existed item")
                .build();

        check.equals(client.getTagsByItem(request).getTagsCount(), 0);
    }

    @Story("Тэг")
    @CaseId(35)
    @Test(description = "Привязка тега к сущности с валидными данными",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createTag")
    public void bindTag() {
        var request = Tagmanager.BindTagRequest.newBuilder()
                .setTagId(tagId)
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId(itemId)
                        .setMeta("something")
                        .build())
                .build();

        var response = client.bindTag(request);
        assertTrue(response.getOk());

        var bind = grpc.getBindsByTag(tagId).get(0);
        check.equals(bind.getTagId(), tagId);
        check.equals(bind.getItemId(), itemId);
        check.equals(bind.getMeta(), "something");
    }

    @Story("Тэг")
    @CaseId(38)
    @Test(description = "Привязка тега с существующей привязкой",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "bindTag")
    public void bindTagDuplicate() {
        var request = Tagmanager.BindTagRequest.newBuilder()
                .setTagId(tagId)
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId(itemId)
                        .setMeta("something new")
                        .build())
                .build();

        var response = client.bindTag(request);
        assertTrue(response.getOk());

        var bind = grpc.getBindsByTag(tagId).get(0);
        check.equals(bind.getTagId(), tagId);
        check.equals(bind.getItemId(), itemId);
        check.equals(bind.getMeta(), "something new");
    }

    @Story("Тэг")
    @CaseId(37)
    @Test(description = "Привязка к несуществующему тегу",
            groups = {"grpc-tag-manager"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: tag_id 2147483647 not found")
    public void bindTagNotFound() {
        var request = Tagmanager.BindTagRequest.newBuilder()
                .setTagId(Integer.MAX_VALUE)
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId(itemId)
                        .setMeta("something")
                        .build())
                .build();

        client.bindTag(request);
    }

    @Story("Тэг")
    @CaseId(40)
    @Test(description = "Привязка тега к нескольким сущностям",
            groups = {"grpc-tag-manager"})
    public void bindTagMultiply() {
        ownerIdMultiplyItems = grpc.createOwner();
        tagIdMultiplyItems = grpc.createTag(ownerIdMultiplyItems, tagNameMultiplyItems);

        var request = Tagmanager.BindTagRequest.newBuilder()
                .setTagId(tagIdMultiplyItems)
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId(itemId + 0)
                        .setMeta(itemId + 0)
                        .build())
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId(itemId + 1)
                        .setMeta(itemId + 1)
                        .build())
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId(itemId + 2)
                        .setMeta(itemId + 2)
                        .build())
                .build();

        var response = client.bindTag(request);
        assertTrue(response.getOk());

        var binds= grpc.getBindsByTag(tagIdMultiplyItems);

        for (int i = 0; i < binds.size(); i++) {
            check.equals(binds.get(i).getTagId(), tagIdMultiplyItems);
            check.equals(binds.get(i).getItemId(), itemId + i);
            check.equals(binds.get(i).getMeta(), itemId + i);
        }
    }

    @Story("Тэг")
    @CaseId(158)
    @Test(description = "Получение списка привязанных тегов по владельцу и нескольким сущностям",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "bindTagMultiply")
    public void getTagsByItems() {
        var request = Tagmanager.GetTagsByItemsRequest.newBuilder()
                .setOwnerId(ownerIdMultiplyItems)
                .addItemsId(itemId + 0)
                .addItemsId(itemId + 1)
                .addItemsId(itemId + 2)
                .build();

        var items = client.getTagsByItems(request).getItemsList();

        System.out.println(items);

        for (int i = 0; i < 3; i++) {
            final int index = i;
            assertTrue(items.stream().anyMatch(item -> item.getItemId().equals(itemId + index)));
        }

        for (var item : items) {
            var tag = item.getTags(0);
            check.equals(tag.getId(), tagIdMultiplyItems);
            check.equals(tag.getName(), tagNameMultiplyItems);
            check.equals(tag.getOwnerId(), ownerIdMultiplyItems);
        }
    }

    @Story("Тэг")
    @CaseId(171)
    @Test(description = "Получение списка привязанных тегов по владельцу и нескольким сущностям, когда у какой-либо сущности несколько тегов",
            groups = {"grpc-tag-manager"})
    public void getTagsByItemsMultiply() {
        int ownerId = grpc.createOwner();
        int tagsNumber = 3;
        for (int i = 0; i < tagsNumber; i++) {
            int tagId = grpc.createTag(ownerId, UUID.randomUUID().toString());
            grpc.bindTag(tagId, itemId);
        }

        var request = Tagmanager.GetTagsByItemsRequest.newBuilder()
                .setOwnerId(ownerId)
                .addItemsId(itemId)
                .build();

        var item = client.getTagsByItems(request).getItems(0);

        check.equals(item.getTagsCount(), tagsNumber);
        item.getTagsList().forEach(tag -> check.equals(tag.getOwnerId(), ownerId));
    }

    @Story("Тэг")
    @CaseId(163)
    @Test(description = "Получение списка, когда часть сущностей существуют, а часть нет",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "bindTagMultiply")
    public void getTagsByItemsPartially() {
        var request = Tagmanager.GetTagsByItemsRequest.newBuilder()
                .setOwnerId(ownerIdMultiplyItems)
                .addItemsId(itemId + 0)
                .addItemsId(itemId + 1)
                .addItemsId(itemId + 2)
                .addItemsId("non existed item")
                .build();

        var items = client.getTagsByItems(request).getItemsList();

        System.out.println(items);

        for (int i = 0; i < 3; i++) {
            final int index = i;
            assertTrue(items.stream().anyMatch(item -> item.getItemId().equals(itemId + index)));
        }
        assertFalse(items.stream().anyMatch(item -> item.getItemId().equals("non existed item")));


        for (var item : items) {
            var tag = item.getTags(0);
            check.equals(tag.getId(), tagIdMultiplyItems);
            check.equals(tag.getName(), tagNameMultiplyItems);
            check.equals(tag.getOwnerId(), ownerIdMultiplyItems);
        }
    }

    @Story("Тэг")
    @CaseId(48)
    @Test(description = "Отвязка сущности от тега с валидными данными",
            groups = {"grpc-tag-manager"},
            dependsOnMethods = "createOwner")
    public void unbindTag() {
        int ownerId = grpc.createOwner();
        int tagId = grpc.createTag(ownerId, UUID.randomUUID().toString());
        grpc.bindTag(tagId, "something to unbind");

        var request = Tagmanager.UnbindTagRequest.newBuilder()
                .setTagId(tagId)
                .addItemsId("something to unbind")
                .build();

        var response = client.unbindTag(request);
        assertTrue(response.getOk());

        check.equals(grpc.getBindsByTag(tagId).size(), 0);
    }
}

