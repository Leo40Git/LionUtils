package adudecalledleo.lionutils.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.*;

/**
 * Helper class for building written books.
 *
 * @since 6.0.0
 */
public final class BookBuilder {
    /**
     * Represents a page in a book.
     */
    public static final class Page {
        /**
         * The contents of the page.
         */
        public final List<Text> lines;

        private Page(Collection<Text> lines) {
            this.lines = new ArrayList<>(lines);
        }

        private Page(Text... lines) {
            this.lines = new ArrayList<>(lines.length);
            Collections.addAll(this.lines, lines);
        }

        private Page() {
            this.lines = new ArrayList<>();
        }
    }

    /**
     * Creates a new page.
     *
     * @param lines
     *         page contents
     * @return a new page that contains the specified lines
     */
    public static Page page(Collection<Text> lines) {
        return new Page(lines);
    }

    /**
     * Creates a new page.
     *
     * @param lines
     *         page contents
     * @return a new page that contains the specified lines
     */
    public static Page page(Text... lines) {
        return new Page(lines);
    }

    /**
     * Creates a new empty page.
     *
     * @return a new empty page
     */
    public static Page emptyPage() {
        return new Page();
    }

    private final String author;
    private final Text title;
    private final List<Page> pages;
    private List<Text> lore;

    private BookBuilder(String author, Text title) {
        this.author = author;
        this.title = title;
        pages = new ArrayList<>();
    }

    /**
     * Creates a new {@code BookBuilder}.
     *
     * @param author
     *         book author
     * @param title
     *         book title
     * @return a new builder instance
     *
     * @since 6.0.1
     */
    public static BookBuilder create(String author, Text title) {
        Objects.requireNonNull(author, "author == null!");
        Objects.requireNonNull(title, "title == null!");
        return new BookBuilder(author, title);
    }

    /**
     * Creates a new {@code BookBuilder}.
     *
     * @param author
     *         book author
     * @param title
     *         book title
     * @return a new builder instance
     */
    public static BookBuilder create(String author, String title) {
        Objects.requireNonNull(author, "author == null!");
        Objects.requireNonNull(title, "title == null!");
        return new BookBuilder(author, new LiteralText(title).styled(style -> style.withItalic(false)));
    }

    /**
     * Adds a page to the resulting book.
     *
     * @param page
     *         page to add
     * @return this builder
     */
    public BookBuilder addPage(Page page) {
        pages.add(page);
        return this;
    }

    /**
     * Adds a new page to the resulting book.
     *
     * @param lines
     *         contents of page to add
     * @return this builder
     */
    public BookBuilder addPage(Collection<Text> lines) {
        return addPage(page(lines));
    }

    /**
     * Adds a new page to the resulting book.
     *
     * @param lines
     *         contents of page to add
     * @return this builder
     */
    public BookBuilder addPage(Text... lines) {
        return addPage(page(lines));
    }

    /**
     * Adds a new empty page to the resulting book.
     *
     * @return this builder
     */
    public BookBuilder addEmptyPage() {
        return addPage(emptyPage());
    }

    private void initLore() {
        if (lore == null)
            lore = new ArrayList<>();
    }

    /**
     * Adds a line of lore to the resulting book.
     *
     * @param line
     *         line of lore to add
     * @return this builder
     */
    public BookBuilder addLore(Text line) {
        initLore();
        lore.add(line);
        return this;
    }

    /**
     * Add lines of lore to the resulting book.
     *
     * @param lines
     *         lines of lore to add
     * @return this builder
     */
    public BookBuilder addLore(Collection<Text> lines) {
        initLore();
        lore.addAll(lines);
        return this;
    }

    /**
     * Add lines of lore to the resulting book.
     *
     * @param lines
     *         lines of lore to add
     * @return this builder
     */
    public BookBuilder addLore(Text... lines) {
        initLore();
        Collections.addAll(lore, lines);
        return this;
    }

    /**
     * Builds a new {@code ItemStack} representing the resulting book.
     *
     * @return a new stack
     */
    public ItemStack build() {
        ItemStack stack = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag tag = stack.getOrCreateTag();
        tag.put("pages", serializePages(pages));
        tag.putString("author", author);
        tag.putString("title", title.getString());
        stack.setCustomName(title);
        if (lore != null)
            ItemStackUtil.setLore(stack, lore);
        return stack;
    }

    private static ListTag serializePages(List<Page> pages) {
        ListTag pagesListTag = new ListTag();
        for (Page page : pages) {
            LiteralText baseText = new LiteralText("");
            final int lineCount = page.lines.size();
            for (int i = 0; i < lineCount; i++) {
                baseText.append(page.lines.get(i));
                if (i < lineCount - 1)
                    baseText.append(new LiteralText("\n"));
            }
            String contents = Text.Serializer.toJson(baseText);
            pagesListTag.add(StringTag.of(contents));
        }
        return pagesListTag;
    }
}
