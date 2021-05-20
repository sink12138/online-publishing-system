using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using UploadFile;

namespace ArticleManager
{
    public partial class UpdateArticleForm : Form
    {
        SqlConnection Sc;
        SqlDataAdapter Sda;
        DataSet Ds;
        DataTable Dt;
        public string newtitle, newauthorid, newcategory, newabstract;
        public bool operationSucceed = false;
        public int rownum=0;
        public UpdateArticleForm()
        {
            InitializeComponent();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select ActiveArticle.articleid,ActiveArticle.title,Author.name,CategoryRelationship.category,ActiveArticle.abstract from ActiveArticle,Author,AuthorRelationship,CategoryRelationship where CategoryRelationship.articleid=ActiveArticle.articleid and ActiveArticle.articleid=AuthorRelationship.articleid and AuthorRelationship.authorid=Author.authorid", Sc);
            Ds = new DataSet();
            Sc.Open();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = Dt;
            dataGridView1.AllowUserToAddRows = false;
            dataGridView1.ReadOnly = true;
            dataGridView1.Columns[0].HeaderCell.Value = "ID";
            dataGridView1.Columns[1].HeaderCell.Value = "标题";
            dataGridView1.Columns[2].HeaderCell.Value = "作者";
            dataGridView1.Columns[3].HeaderCell.Value = "种类";
            dataGridView1.Columns[4].HeaderCell.Value = "摘要";
        }

        private void UpdateAuthor_button_Click(object sender, EventArgs e)
        {
            if (dataGridView1.Rows.Count == 0)
            {
                MessageBox.Show("未选中目标");
                return;
            }
            UpdateAuthor_button.Enabled = false;
            int editID = Convert.ToInt32(dataGridView1.CurrentRow.Cells[0].Value);
            string edittitle = Convert.ToString(dataGridView1.CurrentRow.Cells[1].Value);
            string editauthor = Convert.ToString(dataGridView1.CurrentRow.Cells[2].Value);
            string editcategory = Convert.ToString(dataGridView1.CurrentRow.Cells[3].Value);
            string editabstract = Convert.ToString(dataGridView1.CurrentRow.Cells[4].Value);
            rownum = dataGridView1.CurrentRow.Index;
            UpdateAuthor_button.Enabled = false;
            UpdatingArticleForm uaf = new UpdatingArticleForm(editID, edittitle, editauthor, editcategory, editabstract,rownum);
            uaf.ShowDialog();
            UpdateAuthor_button.Enabled = true;
            //renew
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select ActiveArticle.articleid,ActiveArticle.title,Author.name,CategoryRelationship.category,ActiveArticle.abstract from ActiveArticle,Author,AuthorRelationship,CategoryRelationship where CategoryRelationship.articleid=ActiveArticle.articleid and ActiveArticle.articleid=AuthorRelationship.articleid and AuthorRelationship.authorid=Author.authorid", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = Dt;
            dataGridView1.AllowUserToAddRows = false;
            dataGridView1.ReadOnly = true;
            dataGridView1.Columns[0].HeaderCell.Value = "ID";
            dataGridView1.Columns[1].HeaderCell.Value = "标题";
            dataGridView1.Columns[2].HeaderCell.Value = "作者";
            dataGridView1.Columns[3].HeaderCell.Value = "种类";
            dataGridView1.Columns[4].HeaderCell.Value = "摘要";
            UpdateAuthor_button.Enabled = true;
        }

        private void changeit()
        {
            string articleid = dataGridView1.CurrentRow.Cells[0].Value.ToString();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from ActiveArticle", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("update ActiveArticle set accepted=1 where articleid=@articleid", Sc);
            scmd.Parameters.Add(new SqlParameter("@articleid", SqlDbType.Int, 4, "articleid"));
            Sda.UpdateCommand = scmd;
            foreach (DataRow dr in Dt.Rows)
            {
                if (articleid == dr["articleid"].ToString())
                {
                    try
                    {
                        dr["articleid"] = articleid;
                        Sda.Update(Ds);
                    }
                    catch { };
                }
            }
        }

        private void button5_Click(object sender, EventArgs e)
        {
            if (dataGridView1.Rows.Count == 0)
            {
                MessageBox.Show("未选中目标");
                return;
            }
            button5.Enabled = false;
            string articleid = dataGridView1.CurrentRow.Cells[0].Value.ToString();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection Sc = new SqlConnection(Con);
            SqlDataAdapter Sda = new SqlDataAdapter("select ActiveArticle.articleid,Author.email,ActiveArticle.title from ActiveArticle,AuthorRelationship,Author where ActiveArticle.articleid=AuthorRelationship.articleid and AuthorRelationship.authorid=Author.authorid", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            foreach (DataRow dr in Dt.Rows)
            {
                if (dr[0].ToString() == articleid)
                {
                    System.Diagnostics.Process.Start("mailto:"+dr[1].ToString()+"?subject="+dr[2].ToString());/*
                    button5.Enabled = false;
                    SendEmailForm sef = new SendEmailForm(dr[1].ToString(),dr[2].ToString());
                    sef.ShowDialog();
                    button5.Enabled = true;*/
                    changeit();
                    button5.Enabled = true;
                    return;
                }
            }
            button5.Enabled = true;
        }


        private void change2it()
        {
            string articleid = dataGridView1.CurrentRow.Cells[0].Value.ToString();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from ActiveArticle", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("update ActiveArticle set copyright=1 where articleid=@articleid", Sc);
            scmd.Parameters.Add(new SqlParameter("@articleid", SqlDbType.Int, 4, "articleid"));
            Sda.UpdateCommand = scmd;
            foreach (DataRow dr in Dt.Rows)
            {
                if (articleid == dr["articleid"].ToString())
                {
                    try
                    {
                        dr["articleid"] = articleid;
                        Sda.Update(Ds);
                    }
                    catch { };
                }
            }
        }

        private void button6_Click(object sender, EventArgs e)
        {
            if (dataGridView1.Rows.Count == 0)
            {
                MessageBox.Show("未选中目标");
                return;
            }
            button6.Enabled = false;
            string articleid = dataGridView1.CurrentRow.Cells[0].Value.ToString();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection Sc = new SqlConnection(Con);
            SqlDataAdapter Sda = new SqlDataAdapter("select ActiveArticle.articleid,Author.email,ActiveArticle.title from ActiveArticle,AuthorRelationship,Author where ActiveArticle.articleid=AuthorRelationship.articleid and AuthorRelationship.authorid=Author.authorid", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            foreach (DataRow dr in Dt.Rows)
            {
                if (dr[0].ToString() == articleid)
                {
                    System.Diagnostics.Process.Start("mailto:" + dr[1].ToString() + "?subject=" + dr[2].ToString());/*
                    button5.Enabled = false;
                    SendEmailForm sef = new SendEmailForm(dr[1].ToString(),dr[2].ToString());
                    sef.ShowDialog();
                    button5.Enabled = true;*/
                    change2it();
                    button6.Enabled = true;
                    return;
                }
            }
            button6.Enabled = true;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (dataGridView1.Rows.Count == 0)
            {
                MessageBox.Show("未选中目标");
                return;
            }
            button3.Enabled = false;
            SelectReviewer sr = new SelectReviewer(Convert.ToInt32(dataGridView1.CurrentRow.Cells[0].Value));
            sr.ShowDialog();
            button3.Enabled = true;
        }

        private void button4_Click(object sender, EventArgs e)
        {
            if (dataGridView1.Rows.Count == 0)
            {
                MessageBox.Show("未选中目标");
                return;
            }
            button4.Enabled = false;
            ReciveComment rc = new ReciveComment(Convert.ToInt32(dataGridView1.CurrentRow.Cells[0].Value));
            rc.ShowDialog();
            button4.Enabled = true;
        }

        private void DeleteReviewRelationship(int articleID)
        {
            SqlDataAdapter Sda = new SqlDataAdapter("select * from ReviewerRelationship", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("delete from ReviewerRelationship where articleid=" + Convert.ToString(articleID), Sc);
            Sda.DeleteCommand = scmd;
            for (int i = Dt.Rows.Count-1; i >= 0; i--)
            {
                if (Convert.ToInt32(Dt.Rows[i]["articleid"]) == articleID)
                {
                    Dt.Rows[i].Delete();
                }
            }
            if (Dt.GetChanges() != null) Sda.Update(Dt.GetChanges());
            return;
        }

        private void addauthor(string articleid, string authorid)
        {
            string MyConn = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=OnLineJournalDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection MyConnection = new SqlConnection(MyConn);
            string MyInsert = "insert into Author(articleid,authorid)values(" + articleid + "," + authorid + ")";
            SqlCommand MyCommand = new SqlCommand(MyInsert, MyConnection);
            try//异常处理
            {
                MyConnection.Open();
                MyCommand.ExecuteNonQuery();
                MyConnection.Close();
            }
            catch
            {
            }
        }

        private void DeleteAuthorRelationship(int articleID)
        {
            SqlDataAdapter Sda = new SqlDataAdapter("select * from AuthorRelationship", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("delete from AuthorRelationship where articleid=" + Convert.ToString(articleID), Sc);
            Sda.DeleteCommand = scmd;
            for (int i = 0; i < Dt.Rows.Count; i++)
            {
                if (Convert.ToInt32(Dt.Rows[i]["articleid"]) == articleID)
                {
                    Dt.Rows[i].Delete();
                }
            }
            if (Dt.GetChanges() != null) Sda.Update(Dt.GetChanges());
            return;
        }

        private void DeleteAndAddAuthorRelationship(int articleID)
        {
            SqlDataAdapter Sda = new SqlDataAdapter("select * from AuthorRelationship", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("delete from AuthorRelationship where articleid=" + Convert.ToString(articleID), Sc);
            Sda.DeleteCommand = scmd;
            for (int i = 0; i < Dt.Rows.Count; i++)
            {
                if (Convert.ToInt32(Dt.Rows[i]["articleid"]) == articleID)
                {
                    newauthorid = Dt.Rows[i]["authorid"].ToString();
                    Dt.Rows[i].Delete();
                }
            }
            if (Dt.GetChanges() != null) Sda.Update(Dt.GetChanges());
            return;
        }

        private void addcategory(string articleid, string category)
        {
            string MyConn = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=OnLineJournalDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection MyConnection = new SqlConnection(MyConn);
            string MyInsert = "insert into Category(articleid,category)values(" + articleid + ",'" + category + "')";
            SqlCommand MyCommand = new SqlCommand(MyInsert, MyConnection);
            try//异常处理
            {
                MyConnection.Open();
                MyCommand.ExecuteNonQuery();
                MyConnection.Close();
            }
            catch
            {
            }
        }

        private void DeleteCategoryRelationship(int articleID)
        {
            SqlDataAdapter Sda = new SqlDataAdapter("select * from CategoryRelationship", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("delete from CategoryRelationship where articleid=" + Convert.ToString(articleID), Sc);
            Sda.DeleteCommand = scmd;
            for (int i = 0; i < Dt.Rows.Count; i++)
            {
                if (Convert.ToInt32(Dt.Rows[i]["articleid"]) == articleID)
                {
                    Dt.Rows[i].Delete();
                }
            }
            if (Dt.GetChanges() != null) Sda.Update(Dt.GetChanges());
            return;
        }

        private void DeleteAndAddCategoryRelationship(int articleID)
        {
            SqlDataAdapter Sda = new SqlDataAdapter("select * from CategoryRelationship", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("delete from CategoryRelationship where articleid=" + Convert.ToString(articleID), Sc);
            Sda.DeleteCommand = scmd;
            for (int i = 0; i < Dt.Rows.Count; i++)
            {
                if (Convert.ToInt32(Dt.Rows[i]["articleid"]) == articleID)
                {
                    newcategory = Dt.Rows[i]["category"].ToString();
                    Dt.Rows[i].Delete();
                }
            }
            if (Dt.GetChanges() != null) Sda.Update(Dt.GetChanges());
            return;
        }

        private void addarticle(string articleid,string title,string aabstract)
        {
            string MyConn = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=OnLineJournalDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection MyConnection = new SqlConnection(MyConn);
            string MyInsert = "insert into Article(articleid,title, abstract)values(" + articleid + ",'" + title + "','" + aabstract + "')";
            SqlCommand MyCommand = new SqlCommand(MyInsert, MyConnection);
            try//异常处理
            {
                MyConnection.Open();
                MyCommand.ExecuteNonQuery();
                MyConnection.Close();
            }
            catch
            {
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (dataGridView1.Rows.Count == 0)
            {
                MessageBox.Show("未选中目标");
                return;
            }
            MessageBoxButtons messButton = MessageBoxButtons.OKCancel;
            DialogResult ddr = MessageBox.Show("确定要出版已选中的文章吗?", "出版文章", messButton);
            if (ddr != DialogResult.OK)
            {
                return;
            }
            button1.Enabled = false;
            String id = dataGridView1.CurrentRow.Cells[0].Value.ToString();//待出版文章id
            int selectedID = Convert.ToInt32(dataGridView1.CurrentRow.Cells[0].Value);

            DeleteR(selectedID);
            DeleteAndAddAuthorRelationship(selectedID);
            DeleteAndAddCategoryRelationship(selectedID);

            SqlDataAdapter Sda = new SqlDataAdapter("select * from ActiveArticle", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dtt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("delete from ActiveArticle where ActiveArticle.articleid=" + Convert.ToString(dataGridView1.CurrentRow.Cells[0].Value), Sc);
            Sda.DeleteCommand = scmd;
            for (int i = 0; i < Dtt.Rows.Count; i++)
                if (Convert.ToInt32(Dtt.Rows[i]["articleid"]) == selectedID)
                {
                    addarticle(Dtt.Rows[i]["articleid"].ToString(), Dtt.Rows[i]["title"].ToString(), Dtt.Rows[i]["abstract"].ToString());
                    Dtt.Rows[i].Delete();
                    break;
                }
            if (Dtt.GetChanges() != null) Sda.Update(Dtt.GetChanges());

            renew();

            addcategory(selectedID.ToString(), newcategory);
            addauthor(selectedID.ToString(),newauthorid);
            //

            Upload up = new Upload();
            String targetName1 = "a" + id + ".doc";
            String targetName2 = "a" + id + ".docx";

            //下载远程文件到本地
            String tmp = System.IO.Path.GetTempPath();
            String name = "";
            String remoteURL = @"http://files-1253208676.costj.myqcloud.com/publish/";
            if (up.search_files(targetName1))
            {
                name = tmp + targetName1;
                remoteURL += targetName1;
            }
            else
            {
                name = tmp + targetName2;
                remoteURL += targetName2;
            }

            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(remoteURL);
            HttpWebResponse response = request.GetResponse() as HttpWebResponse;
            Stream responseStream = response.GetResponseStream();
            Stream stream = new FileStream(name, FileMode.Create);
            byte[] bArr = new byte[1024];
            int size = responseStream.Read(bArr, 0, bArr.Length);
            while (size > 0)
            {
                stream.Write(bArr, 0, size);
                size = responseStream.Read(bArr, 0, bArr.Length);
            }
            stream.Close();
            responseStream.Close();

            //删除远程文件
            up.delete_files(targetName1);
            up.delete_files(targetName2);

            //重新上传出版文件
            String newName = id + Path.GetExtension(name);
            up.transfer_files(name, newName);
            MessageBox.Show("出版成功！");
            button1.Enabled = true;
        }

        private void UpdateArticleForm_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }

        void DeleteR(int selectedid)
        {
            string MyConn = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection MyConnection = new SqlConnection(MyConn);
            string MyDelete = "delete from ReviewerRelationship where articleid=" + selectedid.ToString();
            SqlCommand MyCommand = new SqlCommand(MyDelete, MyConnection);
            try
            {
                MyConnection.Open();
                MyCommand.ExecuteNonQuery();
                MyConnection.Close();
            }
            catch
            {
            }
            
        }

        private void renew()
        {
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select ActiveArticle.articleid,ActiveArticle.title,Author.name,CategoryRelationship.category,ActiveArticle.abstract from ActiveArticle,Author,AuthorRelationship,CategoryRelationship where CategoryRelationship.articleid=ActiveArticle.articleid and ActiveArticle.articleid=AuthorRelationship.articleid and AuthorRelationship.authorid=Author.authorid", Sc);
            Ds = new DataSet();
            Sc.Open();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = Dt;
            dataGridView1.AllowUserToAddRows = false;
            dataGridView1.ReadOnly = true;
            dataGridView1.Columns[0].HeaderCell.Value = "ID";
            dataGridView1.Columns[1].HeaderCell.Value = "标题";
            dataGridView1.Columns[2].HeaderCell.Value = "作者";
            dataGridView1.Columns[3].HeaderCell.Value = "种类";
            dataGridView1.Columns[4].HeaderCell.Value = "摘要";
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (dataGridView1.Rows.Count == 0)
            {
                MessageBox.Show("未选中目标");
                return;
            }
            button2.Enabled = false;
            MessageBoxButtons messButton = MessageBoxButtons.OKCancel;
            DialogResult ddr = MessageBox.Show("确定要删除已选中的文章吗?", "删除文章", messButton);
            if (ddr != DialogResult.OK)
            {
                return;
            }
            int selectedID = Convert.ToInt32(dataGridView1.CurrentRow.Cells[0].Value);
            DeleteR(selectedID);
           // DeleteReviewRelationship(selectedID);
            DeleteAuthorRelationship(selectedID);
            DeleteCategoryRelationship(selectedID);


            SqlDataAdapter Sda = new SqlDataAdapter("select * from ActiveArticle", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dtt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("delete from ActiveArticle where ActiveArticle.articleid=" + Convert.ToString(dataGridView1.CurrentRow.Cells[0].Value), Sc);
            Sda.DeleteCommand = scmd;
            for (int i = 0; i < Dtt.Rows.Count; i++)
                if (Convert.ToInt32(Dtt.Rows[i]["articleid"]) == selectedID)
                {
                    addarticle(Dtt.Rows[i]["articleid"].ToString(), Dtt.Rows[i]["title"].ToString(), Dtt.Rows[i]["abstract"].ToString());
                    Dtt.Rows[i].Delete();
                    break;
                }
            if (Dtt.GetChanges() != null) Sda.Update(Dtt.GetChanges());

            renew();


            String id = selectedID.ToString();
            String targetName1 = "a" + id + ".doc";
            String targetName2 = "a" + id + ".docx";
            Upload up = new Upload();
            bool sta1 = up.delete_files(targetName1);
            bool sta2 = up.delete_files(targetName2);

            if (sta1 || sta2)
                MessageBox.Show("删除成功！");
            else
                MessageBox.Show("删除失败！");

            button2.Enabled = true;
        }
    }
}
